import { JSXElement, children, mergeProps } from "solid-js";

interface ButtonProps {
  disable?: boolean,
  onclick: (e: any) => void,
  children: JSXElement
}

const Button = (originalProps: ButtonProps) => {
  const props = mergeProps({
    disable: false
  }, originalProps);

  const c = children(() => props.children);

  const buttonClasses = () => {
    return [
      'text-blue-900',
      'p-2',
      'border border-blue-900',
      'rounded',
      'uppercase text-center',
      props.disable ? 'cursor-not-allowed' : '',
      props.disable ? 'opacity-25' : '',
    ].join(" ")
  }

  return (
    <button
      disabled={props.disable}
      class={buttonClasses()}
      onclick={(e) => props.onclick(e)}
    >
      {c()}
    </button>
  )
}

export default Button;
